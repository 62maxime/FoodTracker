**Create Fridge**
----
Create a fridge in a house according to the provided json

* **URL** `/house/<ID>/fridge`
* **Method:** `POST`
*  **Authentication required:** YES
*  **Permission required:** NO

* **Data Params**
    * id  `str` : id of the fridge
    * maxVolume  `int` : maximum volume of the fridge
    * state  `str` : state of the fridge between "RUNNING", "UNKNOWN", "STOPPED"
* **Success Response:**
  Return the apiKey of the fridge
    * **Code:** 201 <br />
      **Content:** `{ apiKey : <API_KEY> }`

* **Error Response:**
    * **Code:** 401 <br />
      **Content:** `{ error : "Log in" }`

  OR

    * **Code:** 400 <br />
      **Content:** `The house does not exists`
      

* **Sample Call:**
    ```shell
    curl --location --request POST 'https://localhost:8443/house/1/fridge' \
          --header 'Content-Type: application/json' \
          --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA==' \
          --data-raw '{
            "id": "2",
            "state": "RUNNING",
            "maxVolume": 11
          }'
    ```
* **Notes:**
Fridges must have different ids