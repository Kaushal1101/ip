
# Greg User Guide

Greg is a simple task manager for tracking **todos**, **deadlines**, and **events**.

---

## Notes about command format

- Words in `UPPER_CASE` are parameters you provide.  
  Example: `todo DESCRIPTION` → `todo buy groceries`
- Items in `[ ]` are optional.  
  Example: `deadline DESCRIPTION /by DATE [TIME]`
- Date format: `yyyy-mm-dd` (e.g., `2026-03-01`)
- Time format (optional): `HHmm` 24-hour (e.g., `0900`, `1830`)
- Extra parameters for commands like `help`, `list`, `bye` are ignored.  
  Example: `help 123` is treated as `help`.

---

## Quick start example

Try these commands (one per line):

- `todo buy groceries`
- `deadline submit report /by 2026-03-01`
- `event camp /from 2026-03-10 0900 /to 2026-03-12 1800`
- `list`
- `mark 1`
- `find report`
- `bye`

---

## Features

### List tasks
Displays all tasks.

**Format:** `list`  
**Example:** `list`

---

### Add a todo
Adds a todo task.

**Format:** `todo DESCRIPTION`  
**Example:** `todo read chapter 5`

---

### Add a deadline
Adds a deadline task (time optional).

**Format:** `deadline DESCRIPTION /by DATE [TIME]`  
**Examples:**
- `deadline submit report /by 2026-03-01`
- `deadline submit report /by 2026-03-01 1800`

---

### Add an event
Adds an event task (times optional).

**Format:** `event DESCRIPTION /from DATE [TIME] /to DATE [TIME]`  
**Examples:**
- `event camp /from 2026-03-10 /to 2026-03-12`
- `event camp /from 2026-03-10 0900 /to 2026-03-12 1800`

---

### Mark a task
Marks a task as done.

**Format:** `mark TASK_NUMBER`  
**Example:** `mark 2`

---

### Unmark a task
Marks a task as not done.

**Format:** `unmark TASK_NUMBER`  
**Example:** `unmark 2`

---

### Delete a task
Deletes a task.

**Format:** `delete TASK_NUMBER`  
**Example:** `delete 3`

---

### Find tasks
Finds tasks containing a keyword.

**Format:** `find KEYWORD`  
**Example:** `find report`

---

### Help
Shows the help page.

**Format:** `help`  
**Example:** `help`

---

### Exit
Exits Greg.

**Format:** `bye`  
**Example:** `bye`

---

## Error handling

Greg will show an error message if:
- a task number is invalid
- a required parameter is missing
- a date/time format is incorrect
- an unknown command is entered

---

Enjoy using Greg!

---