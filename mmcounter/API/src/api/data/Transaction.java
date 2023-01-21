package api.data;

import java.io.Serializable;
public class Transaction implements Serializable {

    private String id;
    private int amount;

    private String date;

    private String category;

    private boolean isExpense;

    private String description;

    public Transaction(){
    }

    public Transaction(String id, int amount, String date, String category, boolean isExpense, String description){
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.isExpense = isExpense;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
