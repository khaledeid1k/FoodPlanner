package com.example.foodplanner.data.network;



public interface StateOfResponse<T> {
   void succeeded(T response);
   void failure(String message);
}
