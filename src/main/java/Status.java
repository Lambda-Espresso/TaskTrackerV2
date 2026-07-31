package main.java;

public enum Status {
    TO_DO("todo"), IN_PROGRESS("in-progress"), COMPLETED("completed");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
