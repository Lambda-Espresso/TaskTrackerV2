package main.java;

public enum Status {
    TODO("todo"), IN_PROGRESS("in-progress"), DONE("done");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
