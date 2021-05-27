**Create House**
----
Create a house according to the provided json

* **URL** `/house`
* **Method:** `POST`
*  **Authentication required:** YES
*  **Permission required:** NO

* **Data Params**
    * address  `str` : address of the house
    * zip  `str` : country code
* **Success Response:**
  Return the Id of the house
    * **Code:** 201 <br />
      **Content:** `{ id : 12 }`

* **Error Response:**
    * **Code:** 401 <br />
      **Content:** `{ error : "Log in" }`

  OR

    * **Code:** 400 <br />
      **Content:** `A house with the same address exists`

* **Sample Call:**
    ```shell
    curl --location --request POST 'https://localhost:8443/house' \
    --header 'Authorization: Basic ZGVmYXVsdDpwYXNzd29yZA==' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "address": "tot",
        "zip": "1236"
    }'
    ```
* **Notes:**
Houses must have different addresses and zip