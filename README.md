# otp4j
Java Implementation of Time-Based One-Time Password Algorithm(TOTP, RFP6238)

This library provides implementation of not only TOTP but also base RFC HOTP(RFC4226). 
So you can implement original otp algorithm based on HOTP.

## Getting Started
```
String sharedSecretKey = "3132333435363738393031323334353637383930"
String oneTimePassword = (new TOTP()).generate(sharedSecretKey);
```
