# Task Tracker
A CLI app that track your tasks and manage your to-do list. (A challenge from [roadmap.sh](https://roadmap.sh/projects/task-tracker))
# How to run
Clone the repository:
```bash
git clone https://github.com/LambdaEspresso/TaskTrackerV2 && cd TaskTrackerV2/src
```
Compile the source code:
```bash
javac Task.java
```
# Adding a new task
```bash
java Task add "Buy a new laptop"
```
# Updating tasks
```bash
java Task update 1 "Destroy it"
```
# Marking a task as in progress or done
```bash
java Task mark-in-progress 1
java Task mark-done 1
```
# Listing specific task
```bash
java Task list 1
```
# Listing all tasks
```bash
java Task list
```
# Listing tasks by status
```bash
java Task list done
java Task list todo
java Task list in-progress
```
# Deleting specific task
```bash
java Task delete 1
```
# Deleting all tasks
```bash
java Task delete
```
