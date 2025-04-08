import streamlit as st
import jwt  # Correct library for JWT encoding/decoding
from datetime import datetime, timedelta

# Constants for JWT
SECRET_KEY = "your_secret_key_here"
ALGORITHM = "HS256"

# Streamlit App Header
st.title("JWT Generator and Viewer")

# Input Fields for JWT Payload
st.subheader("Enter JWT Payload Details:")
username = st.text_input("Username", key="username_input")
expiration_minutes = st.number_input("Token Expiration (minutes)", min_value=1, value=30, key="expiration_input")

# Button to Generate JWT
if st.button("Fetch and Create JWT", key="fetch_jwt_button"):
    if username:
        # Create JWT Payload
        payload = {
            "sub": username,
            "exp": datetime.utcnow() + timedelta(minutes=expiration_minutes),
            "iat": datetime.utcnow(),
        }
        # Generate JWT Token
        token = jwt.encode(payload, SECRET_KEY, algorithm=ALGORITHM)
        st.success("JWT Created Successfully!")
        st.text_area("Generated JWT Token", token, height=150)
    else:
        st.error("Please enter a username to generate the token.")

# Button to Decode JWT
st.subheader("Decode Existing JWT:")
jwt_token_input = st.text_area("Enter JWT Token to Decode", key="jwt_token_input")
if st.button("Decode JWT", key="decode_jwt_button"):
    if jwt_token_input:
        try:
            # Decode the provided token
            decoded_payload = jwt.decode(jwt_token_input, SECRET_KEY, algorithms=[ALGORITHM])
            st.success("JWT Decoded Successfully!")
            st.json(decoded_payload)
        except jwt.ExpiredSignatureError:
            st.error("The token has expired.")
        except jwt.InvalidTokenError:
            st.error("Invalid token.")
    else:
        st.error("Please enter a JWT token to decode.")
