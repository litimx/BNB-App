package learn.masteryweek.ui;

public enum MainMenuOption {

    VIEW_RESERVATIONS(1, "View Reservations", false),
    ADD_RESERVATION(2, "Add Reservation", false),
    EDIT_RESERVATION(3, "Edit Reservation", false),
    DELETE_RESERVATION(4, "Delete Reservation", false),
    EXIT(0, "Exit Program", false);

    private int value;
    private String message;
    private boolean hidden;

    private MainMenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public boolean isHidden() {
        return hidden;
    }
}