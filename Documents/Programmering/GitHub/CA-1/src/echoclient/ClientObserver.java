/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoclient;

/**
 *
 * @author kristoffernoga
 */
public interface ClientObserver {
    void sendMessage(String message);
    void updateList(String users);
}
