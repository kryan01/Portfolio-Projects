# Predictive Machine Learning for Municipal Shelter Resource Allocation
### Optimizing Capacity for Care Using the Austin Animal Center Dataset

## 📌 Project Overview & Impact
Municipal animal shelters frequently operate at or above maximum capacity, forcing the reactive use of temporary crates in hallways and offices. In a strict "no-kill" system (maintaining a live-release rate of 95% or higher), the primary lever for returning to a sustainable capacity is reducing an animal's average length of stay (LOS).

This project built a supervised machine learning pipeline to act as a **prescriptive triage tool at intake**. By predicting whether an incoming cat or dog faces a high risk of a long-term stay, shelter management can immediately bypass traditional "wait-and-see" periods. Instead, staff can proactively initiate immediate foster placement or targeted social media marketing on Day 1.

### The Risk Tiers (Multi-Class Classification)
* **Nominal Risk:** Stay length up to 1 week.
* **Elevated Risk:** Stay length from 1 week up to 1 month.
* **Severe Risk:** Stay length from 1 to 3 months.
* **Critical Risk:** Stay length of 3 months or longer (the highest consumers of individual shelter resources).

---

## 📊 Data Exploration & Preprocessing
The model utilizes raw intake and outcome datasets from the Austin Animal Center. Significant data cleaning and feature engineering were executed to ensure a high-signal, unbiased training set:

* **Target Bias Elimination:** Removed non-adoption outcomes (e.g., euthanasia, transfer, return-to-owner) to ensure features purely reflected true adoption timelines.
* **Feature Engineering:**
    * Converted raw timestamp strings into uniform DateTime features (Month and Year).
    * Standardized highly volatile age strings into a single integer unit of days.
    * Transformed individual names into a binary variable (`has_name` vs. `no_name` at intake).
    * Decoupled compound reproductive features into independent `sex` and `fixed` variables.
* **Advanced Data Cleaning:** Resolved multi-stay anomalies where system join logic generated duplicate, erroneous historical trajectories (e.g., historical overlaps and "time-traveling" entries) by sorting sequentially by ID and length of stay to isolate legitimate, independent shelter visits.

---

## 🛠️ Modeling & Evaluation Strategy
The machine learning pipeline implemented a **5-fold Stratified K-Fold Cross Validation** to ensure stable representation of the minority "Critical Risk" class across all folds. 

### Addressing Class Imbalance
Because identifying long-stay animals is the highest priority, custom class weights were assigned during training:
* **Nominal & Elevated:** 1.0
* **Severe:** 1.5
* **Critical:** 5.0

> **Evaluation Philosophy:** **Recall** for the "Critical" class was prioritized over precision. In municipal logistics, a False Positive means an animal gets extra marketing resources early—a minor operational cost. A False Negative means a long-term resident goes unnoticed, contributing directly to crisis overcrowding.

Five distinct architectures were tested, tuned via automated GridSearch, and scaled appropriately:
1.  Logistic Regression *(Baseline)*
2.  Decision Tree Classifier
3.  Random Forest Classifier
4.  Artificial Neural Network (ANN) *(Sequential layout with a 10-node hidden layer)*
5.  **Histogram-Based Gradient Boosting Classifier (HGBC) — *[CHOSEN MODEL]***

---

## 🏆 Final Results
The **Histogram-Based Gradient Boosting Classifier (HGBC)** outperformed the alternative architectures, proving highly effective at parsing complex tabular structures without memorizing noise.

* **Training Accuracy:** 48.52%
* **Testing Accuracy:** 46.23%
* **Generalization Gap:** Minimal (2.29%), indicating excellent variance control and out-of-sample reliability.
* **Operational Success:** The final model successfully flags **67% of all Critical Risk animals on Day 1**, providing an actionable mechanism to transition shelter operations from reactive crisis management to proactive resource allocation.

---

## 📂 Repository Organization
```text
├── Animal-Shelter-ML/
│   ├── README.md                  <-- Executive summary and operational results
│   ├── Final_Report.pdf           <-- Complete academic and analytical breakdown
│   ├── Semester_Presentation.pdf  <-- Polished slide deck summarizing findings
│   └── ML_Shelter_Prediction.ipynb <-- Production Jupyter Notebook containing full codebase
│   └── data/                      <-- Dataset directory
│       ├── AAC_Intakes.csv        <-- Raw animal intake dataset
│       └── AAC_Outcomes.csv       <-- Raw animal outcome dataset
