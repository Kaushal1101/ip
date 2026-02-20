# AI Usage Declaration

## Overview

I used AI as a development assistant to improve productivity and accelerate implementation, while retaining full ownership of system design, architecture, and core logic decisions. AI was used to support mechanical and repetitive coding tasks, not to replace understanding or design reasoning.

---

## How AI Was Used

AI was primarily used for:

- Assisting with command parsing logic (e.g., extracting arguments, handling indexes, validating input)
- Refactoring code to better follow SLAP and DRY principles
- Generating and refining Javadoc comments
- Improving error handling consistency
- Helping interpret build/runtime errors and resolving Git merge conflicts
- Drafting structured commit messages

These tasks were largely mechanical in nature. I provided the intended behavior and constraints, and AI helped translate them into structured implementations more efficiently.

---

## My Role in Architecture and Core Design

The overall architecture and class responsibilities were designed and decided by me. This includes:

- The separation into `model`, `logic`, `ui`, `storage`, and `exception` layers
- The flow of commands (`Parser → Greg → TaskList → Ui`)
- The task model hierarchy (`Task`, `Todo`, `Deadline`, `Event`)
- Persistence design and save-file format
- Feature decisions such as the help command and enum-based command handling

AI was not used to plan or decide system architecture. High-level structure and design tradeoffs were determined through my own understanding of maintainability and project requirements.

---

## What Worked Well

AI was especially helpful for repetitive implementation work and refactoring passes. It significantly reduced the time spent on boilerplate, formatting, and documentation, and acted as a useful tool for catching small inconsistencies.

---

## Conclusion

AI was used responsibly as a productivity tool. All architectural decisions, structural organization, and final implementations were guided and validated by me. I understand all code included in this project and can explain all design choices made.
