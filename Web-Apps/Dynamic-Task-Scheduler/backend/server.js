const express = require('express');
const fs = require('fs');
const path = require('path');
const cors = require('cors');

const app = express();
const PORT = 3001;
const DATA_FILE = path.join(__dirname, 'tasks.json'); // This is where our to-do list lives

// Let anyone talk to the server, and let Express understand JSON
app.use(cors());
app.use(express.json());

/*
  GET all tasks
  (reads tasks.json and sends the whole list back)
*/
app.get('/api/tasks', (req, res) => {
  fs.readFile(DATA_FILE, 'utf-8', (err, data) => {
    if (err) {
      console.error('Error reading tasks file:', err);
      return res.status(500).json({ error: 'Internal server error' });
    }
    res.json(JSON.parse(data));
  });
});

/*
  POST a new task (primary tasks only)
  (grab what was sent in, give it a unique id, and save it to the file)
*/
app.post('/api/tasks', (req, res) => {
  const newTask = req.body;
  fs.readFile(DATA_FILE, 'utf-8', (err, data) => {
    if (err) return res.status(500).json({ error: 'Read error' });
    const tasks = JSON.parse(data);
    newTask.id = Date.now(); // make a unique id
    tasks.push(newTask);
    fs.writeFile(DATA_FILE, JSON.stringify(tasks, null, 2), (err) => {
      if (err) return res.status(500).json({ error: 'Write error' });
      res.status(201).json(newTask); // Send back the created task
    });
  });
});

/*
  PUT (update) a task by id
  (find the task, merge in the new info, and save the file)
  [edits, completions, priority, and subtasks]
*/
app.put('/api/tasks/:id', (req, res) => {
  const taskId = req.params.id;
  const updatedTask = req.body;

  fs.readFile(DATA_FILE, 'utf-8', (err, data) => {
    if (err) return res.status(500).json({ error: 'Read error' });
    const tasks = JSON.parse(data);
    const index = tasks.findIndex((t) => t.id === Number(taskId));

    if (index === -1) {
      return res.status(404).json({ error: 'Task not found' });
    }

    tasks[index] = { ...tasks[index], ...updatedTask };

    fs.writeFile(DATA_FILE, JSON.stringify(tasks, null, 2), (err) => {
      if (err) return res.status(500).json({ error: 'Write error' });
      res.json(tasks[index]); // Send back the updated task
    });
  });
});

/*
  DELETE a task by id
  (remove the task from the list and save the rest)
*/
app.delete('/api/tasks/:id', (req, res) => {
  const taskId = req.params.id;

  fs.readFile(DATA_FILE, 'utf-8', (err, data) => {
    if (err) return res.status(500).json({ error: 'Read error' });
    let tasks = JSON.parse(data);
    const filtered = tasks.filter((t) => t.id !== Number(taskId));

    if (filtered.length === tasks.length) {
      return res.status(404).json({ error: 'Task not found' });
    }

    fs.writeFile(DATA_FILE, JSON.stringify(filtered, null, 2), (err) => {
      if (err) return res.status(500).json({ error: 'Write error' });
      res.status(204).end(); // Success! No body needed
    });
  });
});

// start up the server
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
