package com.example.resourceserver.greeting;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GreetingCardUser {
    private String username;
    private boolean isAdmin;
    private String nearestStore;
    private String card;
    private boolean isAbleToPurchase;

    public GreetingCardUser(GreetingCardUser user) {
        setUsername(user.getUsername());
        setAdmin(user.isAdmin());
        setNearestStore(user.getNearestStore());
        setCard(user.getCard());
        setAbleToPurchase(user.isAbleToPurchase());
    }
}
