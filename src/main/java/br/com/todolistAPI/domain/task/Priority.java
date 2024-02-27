package br.com.todolistAPI.domain.task;

public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isPriority(Priority priority){
        for (Priority p : Priority.values()){
            if (priority.getLabel().equalsIgnoreCase(p.getLabel()))
                return true;
        }
        return false;
    }
}
