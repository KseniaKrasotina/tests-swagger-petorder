package storeAPI;

import com.google.gson.Gson;

public class OrderDTO {
    public Integer id;
    public Integer petId;
    public Integer quantity;
    public String shipDate;
    public OrderStatus status;
    public Boolean complete;

    public OrderDTO(int id, int petId, int quantity, String shipDate, OrderStatus status, Boolean complete)
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

enum OrderStatus {
    placed, appoved, delivered
}
