/**
 * The Fur Seasons Resort app lets pet parents book and manage boarding,
 * daycare, and grooming services for dogs and cats — complete with dynamic pricing, add‑ons,
 * and client profiles.
 * @authors Daisy Medina and Kayla Ryan
 */
package com.furseasonsresort.semesterproject;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.InputStream;

public class FurSeasonsResort extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
    // logo
        ImageView logoView;
        try (InputStream logoStream = getClass().getResourceAsStream("images/FurSeasonsLogo.png"))
        {
            if (logoStream != null)
            {
                Image logoImage = new Image(logoStream);
                logoView = new ImageView(logoImage);
                logoView.setFitWidth(400);
                logoView.setPreserveRatio(true);
            }

            else
            {
                logoView = new ImageView();
            }
        }
        catch (Exception e)

        {
            logoView = new ImageView();
        }

    // title
        Label title = new Label("Premier Pet Boarding & Daycare");
        title.setStyle("-fx-font-family: \"Cinzel\", serif; " + "-fx-font-size: 24px; " + "-fx-text-fill: #748972;");

    // pricing
        Button catPricingBtn = new Button("Cat Pricing");
        catPricingBtn.setStyle("-fx-text-fill: #748972;");
        catPricingBtn.setOnAction(e -> PricingController.showCatPricingWindow());

        Button dogPricingBtn = new Button("Dog Pricing");
        dogPricingBtn.setStyle("-fx-text-fill: #748972;");
        dogPricingBtn.setOnAction(e -> PricingController.showDogPricingWindow());

        HBox pricingBox = new HBox(20, catPricingBtn, dogPricingBtn);
        pricingBox.setAlignment(Pos.CENTER);

    // Navigation links
        Hyperlink servicesLink = new Hyperlink("Services");
        servicesLink.setStyle("-fx-text-fill:#8f5681; -fx-font-size:15px;");
        servicesLink.setOnAction(e -> ServicesController.showServicesWindow());

        Hyperlink reservationLink = new Hyperlink("Reservation");
        reservationLink.setStyle("-fx-text-fill:#8f5681;-fx-font-size:15px;");
        reservationLink.setOnAction(e -> ReservationController.showReservationWindow());

        Hyperlink accountLink = new Hyperlink("Account");
        accountLink.setStyle("-fx-text-fill:#8f5681; -fx-font-size:15px;");
        accountLink.setOnAction(e -> AccountController.showAccountScene());

        Hyperlink otherLink = new Hyperlink("Follow Us");
        otherLink.setStyle("-fx-text-fill:#8f5681; -fx-font-size:15px;");
        otherLink.setOnAction(e -> SocialsController.showOtherPagesWindow());

        Label s1 = new Label("|");      s1.setStyle("-fx-font-size:15px; -fx-text-fill: #748972;");
        Label s2 = new Label("|");      s2.setStyle("-fx-font-size:15px; -fx-text-fill: #748972;");
        Label s3 = new Label("|");      s3.setStyle("-fx-font-size:15px; -fx-text-fill: #748972;");
        HBox navBox = new HBox(50, servicesLink, s1, reservationLink, s2, accountLink, s3, otherLink);
        navBox.setAlignment(Pos.CENTER);
        VBox.setMargin(navBox, new Insets(0, 0, 20, 0));

    // About Us section
      // Story
        Label storyLabel = new Label("Our Story");
        storyLabel.setStyle("-fx-font-size:20px; -fx-text-fill:#79748b; -fx-font-weight:bold;");
        Text storyText = new Text("Fur Seasons Resort was born from a love of pets and the changing beauty of nature. " +
                        "Inspired by the seasonal blooming springs, sun-soaked summers, crisp autumns, " +
                        "and cozy winters — we’ve created a playful, spa-like retreat where dogs and cats feel right at home.\n"
        );
        storyText.setWrappingWidth(600);
        storyText.setFill(Color.web("#5e6f5d"));
        storyText.setStyle("-fx-font-size: 14px;");

      // Mission
        Label missionLabel = new Label("Our Mission");
        missionLabel.setStyle("-fx-font-size:20px; -fx-text-fill:#79748b; -fx-font-weight:bold;");
        Text missionText = new Text("To provide a safe, nurturing, and enriching environment for every furry guest, combining " +
                        "the comfort of home with the fun of a boutique resort. We aim to ease owners’ minds by " +
                        "offering transparent pricing, personalized care, and a seamless booking experience.\n"
        );
        missionText.setWrappingWidth(600);
        missionText.setFill(Color.web("#5e6f5d"));
        missionText.setStyle("-fx-font-size: 14px;");

      // Services
        Label servicesLabel = new Label("Services Offered");
        servicesLabel.setStyle("-fx-font-size:20px; -fx-text-fill:#79748b; -fx-font-weight:bold;");
        Text servicesText = new Text("• Daycare: Supervised playgroups, enrichment activities, and cozy nap zones for daytime fun.\n" +
                        "• Boarding: Spacious suites with bedding, twice-daily walks, and optional “catio” time.\n" +
                        "• Grooming Extras: Baths, basic grooming services, and spa add‑ons.\n" +
                        "• Multi‑Pet Discounts: Reduced rates for families with more than one pet.\n" +
                        "• Custom Profiles: Owners can save pet details, booking history, and preferences for faster checkout.\n"
        );
        servicesText.setWrappingWidth(600);
        servicesText.setFill(Color.web("#5e6f5d"));
        servicesText.setStyle("-fx-font-size: 14px;");

    // Why Us
        Label whyLabel = new Label("Why Fur Seasons?");
        whyLabel.setStyle("-fx-font-size:20px; -fx-text-fill:#79748b; -fx-font-weight:bold;");
        Text whyText = new Text("Because every pet deserves a vacation, too. Whether they’re chasing leaves in autumn or " +
                        "lounging in a sunbeam in summer, your best friend will enjoy personalized attention, healthy " +
                        "treats, and plenty of play—no matter the season."
        );
        whyText.setWrappingWidth(600);
        whyText.setFill(Color.web("#5e6f5d"));
        whyText.setStyle("-fx-font-size: 14px;");

      // build VBox to hold About Us sections
        VBox aboutSection = new VBox(10,
                storyLabel, storyText,
                missionLabel, missionText,
                servicesLabel, servicesText,
                whyLabel, whyText
        );
        aboutSection.setAlignment(Pos.TOP_LEFT);


    // Business Address, formatted
        Label businessName = new Label("\n\nFur Seasons Resort");
        businessName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill:#8f5681;");
        businessName.setAlignment(Pos.CENTER);

        String streetAddress = "1423 Groomers Grove";
        String cityStateZip = "Pawshire, TX 00000";
        Label addressLabel = new Label( streetAddress + "\n" + cityStateZip);
        addressLabel.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill:#8f5681;"
        );
        addressLabel.setTextAlignment(TextAlignment.CENTER);

        VBox physicalAddress = new VBox(businessName, addressLabel);
        physicalAddress.setAlignment(Pos.CENTER);

        VBox.setMargin(aboutSection, new Insets(50, 0, 0, 0));

    // main layout
        VBox mainBox = new VBox(18, navBox, logoView, title, pricingBox, aboutSection, physicalAddress);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(40));
        mainBox.setStyle("-fx-background-color:#fcefdb;");

    // enable scroll bar
        ScrollPane scrollPane = new ScrollPane(mainBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background:transparent; -fx-background-color:transparent;");

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(scrollPane);
        rootPane.setStyle("-fx-background-color:#fcefdb;");


        Scene scene = new Scene(rootPane, 800, 1100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fur Seasons Resort");
        primaryStage.show();
    }

 // get: physical address
    public static Text getAddress()
    {
        Text businessName = new Text("Fur Seasons Resort\n1423 Groomers Grove\nPawshire, TX 00000");
        businessName.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill:#8f5681;"
        );
            businessName.setTextAlignment(TextAlignment.CENTER);

        return businessName;
    }

// launch page
    public static void main(String[] args)
    {
        launch(args);
    }
}
