package com.vcu.ramhacks.service;

import com.vcu.ramhacks.domain.Purchase;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Created by kylecrane on 9/18/16.
 */
@Service
public class CapitalOneService {

    private Logger log = LoggerFactory.getLogger(CapitalOneService.class);

    private RestTemplate restTemplate = new RestTemplate();

    private String customerUri = "http://api.reimaginebanking.com/customers/57de2936e63c5995587e8e48?key=7d4bdd60242f8af5bdbde8437bd59a32";

    private String customerAccountUri = "http://api.reimaginebanking.com/customers/57de2936e63c5995587e8e48/accounts?key=7d4bdd60242f8af5bdbde8437bd59a32";

    private String customerPurchases = "http://api.reimaginebanking.com/accounts/57de2f6ee63c5995587e8e53/purchases?key=7d4bdd60242f8af5bdbde8437bd59a32";


    public String getCustomerAccount(){

        String result = restTemplate.getForObject(customerAccountUri, String.class);

        log.info("Customer Account: " + result);

        return result;

    }


    public Purchase[] getListOfPurchasesByCustomer(){

        ResponseEntity<Purchase[]> responseEntity = restTemplate.getForEntity(customerPurchases, Purchase[].class);

        Purchase[] purchases = responseEntity.getBody();

        for(Purchase purchase : purchases){
            log.info("Customer Purchases: " + purchase.toString());
        }

        return purchases;

    }


    public String getCustomerValue(String key){

        JSONObject jsonCustomer = convertCustomerToJSONObject(restTemplate.getForObject(customerUri, String.class));

        String value;

        try{
            value = (String)jsonCustomer.get(key);
        }catch (JSONException e){
            log.info("key [" + key + "] was unable to retrieve from jsonCustomer");
            value = "";
            e.printStackTrace();
        }

        log.info("Customer " + key + ": " + value);

        return value;

    }


    public JSONObject convertCustomerToJSONObject(String customer){

        JSONObject resultToJSON;

        try {
            resultToJSON = new JSONObject(customer);
        }catch(JSONException e) {
            resultToJSON = new JSONObject();
        }

        return resultToJSON;

    }

}
