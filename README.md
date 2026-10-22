# Payment Terminal : Java Fundamentals

## Learning Outcomes

| Learning Outcome | Section |
|---|---|
| Project Management / Agile | Section 1 |
| Brownfields Development | Section 2 |
| Testing | Section 3 |
| Systems Design | Section 4 |

---

## Duration

**Total time: 2 hours 30 minutes**

| Section | Recommended Time |
|---|---|
| Section 1 — Project Management | 40 minutes |
| Section 2 — Brownfields Development | 65 minutes |
| Section 3 — Testing | 25 minutes |
| Section 4 — Systems Design | 20 minutes |

---

## Scoring

| Section | Marks |
|---|---|
| Section 1 — Project Management | 25 |
| Section 2 — Brownfields Development | 40 |
| Section 3 — Testing | 20 |
| Section 4 — Systems Design | 15 |
| **Total** | **100** |

---

# Section 1 — Project Management

## Scenario

Google — Trunk-Based Development in a Single Repository

Google's engineers keep the company's source code in one very large shared repository and, for
the most part, develop against a single shared trunk instead of on long-lived branches. Every change
is small, is checked by automated tests before it lands, and unfinished work is hidden behind configuration
flags instead of being kept apart on a branch for weeks. The result is that everyone is always working
against the latest version of everyone else's code, so problems from combining work are found within
hours rather than at the end of a long branch's life, and there is no painful "big merge" before a release.

You have been brought in as project manager for a new team about to start a similar project:
building a **Payment Terminal Manager** for QuickTap Payments, a company that supplies card machines to
small shops. Terminals in the field need to authorise a card payment and, if the payment cannot be completed
(for example the capture step fails), safely reverse the authorisation so the customer is not charged.

Your first responsibility, before any code is written, is to set the team's way of working —
including how they will branch and integrate their code, so they do not end up with weeks of diverging
branches that have to be merged together before every release.

> **Reference:** Potvin, R. and Levenberg, J. (2016). *Why Google Stores Billions of Lines of Code in a Single Repository.* Communications of the ACM, 59(7).

| Task | Marks | What earns marks |
|---|---|---|
| Q1.1 — Waterfall vs Agile & Versioning | 5 | Accurate definition of both (2), clear reasoning connecting a branching/integration strategy to the shift, specific to this team (3) |
| Q1.2 — Taskboard | 14 | Lane completeness and logic covering full backlog-to-delivery flow (8), lanes are genuinely distinct and sensibly named, not copied from the curriculum example (4), default Open/Closed columns correctly removed as instructed (2) |
| Q1.3 — Tickets and user stories | 6 | Correct user story format (2), story reflects the actual actor and goal from the scenario for each ticket (4) |

---

### Q1.1 — Waterfall vs Agile & Versioning *(5 marks)*

Define and contrast the Waterfall model and Agile (time-boxed) delivery.
The risk for the Payment Terminal team is not writing the code — it is the cost of
combining work that has been developed apart for too long. Explain how working on a single shared
trunk with continuous integration supports Agile delivery in a way long-lived feature branches
work against, and why Waterfall's phase-gated structure would make that integration problem worse,
not better.

---

### Q1.2 — Set Up the Taskboard *(14 marks)*

Drawing on your experience with code clinics and Robot Worlds as part of your project management 
journey, set up a GitLab taskboard for this team. 
Design the lanes and columns yourself so that together they reflect the full 
journey of a piece of work from start to finish — including separate visibility for work
that is ready for development, ready for testing, and ready for deployment, not just a single 
"Doing" lane.

> GitLab's default Open and Closed columns do not count as lanes and must
> not appear when you submit your board. Every lane must be one you have deliberately 
> created and named.

---

### Q1.3 — Create the Tickets *(6 marks)*

Create two tickets on your board:

- **Ticket 1:** Terminal authorises a card payment
- **Ticket 2:** Terminal reverses a payment after a failed capture

Under each ticket, write a user story describing what that piece of functionality is looking to 
achieve, from the user's perspective.

---

# Section 2 — Brownfields Development

| Task | Marks | What earns marks |
|---|---|---|
| Q2.1 — Build Scripts | 7 | Accurate definition of a build script and what it entails (3), clear explanation of the problem it solves vs. IDE-only manual builds (2), Robot Worlds Makefile example genuinely illustrates the concept (2) |
| Q2.2 — Refactoring | 7 | Correct definition (2), correct before/during/after conditions (2), clear refactor-vs-rewrite distinction (2), explains why disciplined refactoring matters once a team integrates continuously on a shared trunk (1) |
| Q2.3 — Refactoring for Maintainability | 26 | Q2.3.1 asked once, covering both methods: accurate definition/contrast of static vs. behavioural code analysis (3), correctly classifies which activities in this exercise are static vs. behavioural (3). Remaining 20 split across two methods, 10 each: refactor actually resolves the maintainability problem (4), tests pass unmodified — binary (4), written justification is specific to their change (2) |

---

### Q2.1 — Build Scripts *(7 marks)*

Define what a build script is, and explain what it entails — which tasks it needs to be able to run,
and why those tasks belong in a script rather than being typed by hand each time. QuickTap's trunk
is built by a continuous integration server that has no IDE installed. What problems does a build
script solve for a team compared to a project that can only be built inside an IDE on one
developer's machine? Use a specific example from your own experience creating a Makefile for Robot
Worlds (for example one of its targets) to illustrate your answer.

---

### Q2.2 — Refactoring *(7 marks)*

Define refactoring. Explain the process a developer should follow when refactoring code — what
must stay true before, during, and after the change — and explain why refactoring is treated 
differently from a rewrite. Then explain why small, disciplined, frequent refactoring becomes 
especially important once a team commits continuously to a single shared trunk, rather than 
isolating changes on long-lived branches.

---

### Q2.3 — Refactoring for Maintainability *(26 marks)*

Somewhere in the provided codebase there are two methods, each with a distinct maintainability
problem in how its conditional logic is structured.

#### Q2.3.1 — Code Analysis

Define static code analysis and behavioural (dynamic) code analysis, and explain 
the key difference between them — what each one examines, and what each one can and 
cannot tell you about a piece of code. Then classify the activities you carry out in the 
rest of this question: is finding a method's structural problem by reading it static or 
behavioural analysis? Is running `mvn test` to confirm nothing broke static or behavioural 
analysis? *(Write this in your `.txt` file — answer once, covering both methods.)*

For each of the two methods, complete Q2.3.2 and Q2.3.3 below.

#### Q2.3.2 — Refactor

Refactor the method to resolve its maintainability problem.

#### Q2.3.3 — Prove behaviour preservation

Run the existing test suite before and after your refactor:

```bash
mvn test
```

Your refactored code must pass all existing tests unchanged. 
You may not modify the tests to make them pass.

Alongside your refactored code, include a short written explanation of 3 to 5 sentences 
per method justifying why your new structure is more maintainable than the original.

---

# Section 3 — Testing

| Task | Marks |
|---|---|
| Q3.1 — Types of Testing | 12 |
| Q3.2 — Test Reports | 8 |

---

### Q3.1 — Types of Testing *(12 marks)*

Every change to the QuickTap terminal software passes through three
checks before it can land on trunk: one suite exercises the single method `canAuthorise` in 
isolation with no other classes involved; a second suite runs the full authorise-capture-reverse flow
end-to-end against a simulated bank gateway; a third suite checks the finished terminal's behaviour
against the published card-message specification, with no knowledge of how it was built internally.
Name the type of testing each of these three checks represents, define each type, 
and explain what each one is actually confirming that the other two are not.

---

### Q3.2 — Test Reports *(8 marks)*

When you run `mvn test`, Maven generates a test report via Surefire summarising 
the results of your test suite. What key pieces of information would you expect to 
find in a test report? If yesterday's report on trunk showed 240 tests run and today's shows
90 tests run, with every test passing, what might that suggest about the reliability of "all 
tests passed" as a sign the terminal software works? And why is it useful to look at a test
report over time — across multiple commits to trunk — rather than just checking the latest one?

---

# Section 4 — Systems Design

| Task | Marks |
|---|---|
| Q4.1 — System Design | 5 |
| Q4.2 — UML Diagrams | 4 |
| Q4.3 — Sequence Diagram | 6 |

---

### Q4.1 — System Design *(5 marks)*

Define what system design — also referred to as software design — means, and 
explain what it aims to achieve.

---

### Q4.2 — UML Diagrams *(4 marks)*

Define what a UML diagram is. Give two examples of different kinds of
UML diagrams and explain what each one aims to achieve.

---

### Q4.3 — Sequence Diagram *(6 marks)*

Draw a UML sequence diagram for the following flow from the Section 1 scenario: a customer 
taps a card, the terminal asks for the payment to be authorised, the payment is authorised, the
capture step then fails partway through, and the terminal reverses the authorisation so the customer
is not charged. Show the objects involved (for example, the customer or card, the terminal, the payment
service and the bank gateway) and the sequence of messages between them, from the initial tap through
to the completed reversal.

Use your project gitlab wiki to create your drawing. No external links can be used.

---
### End of Assessment
---

## Project structure

```
src/main/java/com/mocoder/paymentterminal/
├── Main.java                    # Demo entry point
├── model/                       # Domain classes (Terminal, PaymentRequest, PaymentAttempt, AuthorisationHold, CardType)
└── service/                     # PaymentService and supporting types
src/test/java/com/mocoder/paymentterminal/
├── model/                       # JUnit 5 tests for the domain classes
└── service/                     # JUnit 5 tests for PaymentService
```

## Useful Commands You Can Run

Compile the project:

```
mvn compile
```

Package it into a jar:

```
mvn package
```

## Running the tests

```
mvn test
```

## Running the demo

After compiling, run the `Main` class directly:

```
mvn compile
java -cp target/classes com.mocoder.paymentterminal.Main
```
