// backend/routes/tasks.js
const express = require('express');
const fs = require('fs-extra');
const path = require('path');

const router = express.Router();
const DB = path.join(__dirname, '..', 'tasks.json');

// Helpers
async function readTasks() {
  return fs.readJson(DB);
}
async function writeTasks(data) {
  return fs.writeJson(DB, data, { spaces: 2 });
}

// GET all
router.get('/', async (req, res, next) => {
  try {
    const tasks = await readTasks();
    res.json(tasks);
  } catch (err) {
    next(err);
  }
});

// POST new
router.post('/', async (req, res, next) => {
  try {
    const tasks = await readTasks();
    const newTask = {
      id: Date.now(),
      title: req.body.title,
      completed: false,
      subtasks: req.body.subtasks || []
    };
    tasks.push(newTask);
    await writeTasks(tasks);
    res.status(201).json(newTask);
  } catch (err) {
    next(err);
  }
});

// PUT update
router.put('/:id', async (req, res, next) => {
  try {
    const id = Number(req.params.id);
    let tasks = await readTasks();
    const idx = tasks.findIndex(t => t.id === id);
    if (idx === -1) return res.status(404).json({ error: 'Task not found' });
    tasks[idx] = { ...tasks[idx], ...req.body };
    await writeTasks(tasks);
    res.json(tasks[idx]);
  } catch (err) {
    next(err);
  }
});

// DELETE
router.delete('/:id', async (req, res, next) => {
  try {
    const id = Number(req.params.id);
    let tasks = await readTasks();
    const filtered = tasks.filter(t => t.id !== id);
    if (filtered.length === tasks.length)
      return res.status(404).json({ error: 'Task not found' });
    await writeTasks(filtered);
    res.status(204).end();
  } catch (err) {
    next(err);
  }
});

module.exports = router;