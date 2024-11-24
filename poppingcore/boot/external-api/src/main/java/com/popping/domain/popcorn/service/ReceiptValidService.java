//package com.popping.domain.popcorn.service;
//
//import com.apple.itunes.storekit.client.AppStoreServerAPIClient;
//import com.apple.itunes.storekit.model.Environment;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ReceiptValidService {
//    String issuerId = "99b16628-15e4-4668-972b-eeff55eeff55";
//    String keyId = "ABCDEFGHIJ";
//    String bundleId = "com.example";
//    Path filePath = Path.of("/path/to/key/SubscriptionKey_ABCDEFGHIJ.p8");
//    String encodedKey = Files.readString(filePath);
//    Environment environment = Environment.SANDBOX;
//
//    AppStoreServerAPIClient client =
//            new AppStoreServerAPIClient(encodedKey, keyId, issuerId, bundleId, environment);
//}
