public enum Status {
    TODO("todo"), IN_PROGRESS("in_progress"), DONE("done");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
