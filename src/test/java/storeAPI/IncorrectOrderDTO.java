package storeAPI;

import com.google.gson.Gson;

public class IncorrectOrderDTO {
    public Integer id;
    public String petId;
    public Integer quantity;
    public Integer shipDate;
    public OrderStatus status;
    public Boolean complete;

    public IncorrectOrderDTO(int id, String petId, int quantity, int shipDate, OrderStatus status, Boolean complete)
    {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }


    public String toJson() {
        Gson gson = new Gson();
        String result = gson.toJson(this);
        return result;
    }
}
