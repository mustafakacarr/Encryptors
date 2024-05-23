# Encryption and Decryption Service

## Overview

This project provides a secure mechanism for generating encryption keys, encrypting and decrypting text data, and safely transmitting these encrypted keys and messages over HTTP. The service includes the following key functionalities:

1. **Encryption Key Generation**
2. **Data Encryption**
3. **Encoding Key for HTTP Headers**
4. **Key Transmission and Note Creation**
5. **URL Formation**
6. **Data Decryption**

## Tasks

### 1. Encryption Key Generation
- **Objective:** Develop a function to generate encryption keys.
- **Specifications:** The key should be a random combination of letters and digits, up to 8 characters in length.

### 2. Data Encryption
- **Objective:** Utilize the generated key to encrypt text data.
- **Technology:** AES encryption algorithm.
- **Additional:** Encode the encryption result in Base64 to ensure safe data transmission and storage.

### 3. Encoding Key for HTTP Headers
- **Objective:** Encode the encryption key in Base64.
- **Additional:** Further encrypt this Base64 string using AES and a static key to enhance security during HTTP header transmission.

### 4. Key Transmission and Note Creation
- **Endpoint:** `/create`
- **Actions:** Transmit the encrypted message to the server and send the encrypted key in HTTP headers under the name `X-Timestamp`. Implement similar pseudotimestamp-based headers in other requests for consistency and security.

### 5. URL Formation
- **Objective:** Generate a URL for accessing the encrypted note.
- **Example:** `https://secret.com/12345#key`

### 6. Data Decryption
- **Objective:** Enable the client-side decryption of data.
- **Method:** Extract the key from the URL following the '#' symbol to decrypt the message contents.

## Additional Instructions

### Security
- Ensure maximal security in the storage and transmission of the key.

### Data Handling
- Correct data encoding to prevent operational errors.

### Integration of Pseudotimestamp
- **Objective:** Append a pseudotimestamp to all requests to ensure session uniqueness and security.

## Project Additions Required

### Encryption Module
- Implement a module for key generation, text encryption, and Base64 handling.

### Client-side Updates
- Implement mechanisms for key extraction and use on the client side.

### Dependency Management
- Add necessary libraries for encryption and data encoding.



