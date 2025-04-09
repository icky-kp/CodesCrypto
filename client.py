import socket
import ssl

HOST = '127.0.0.1'
PORT = 8443

# Create an SSL context
context = ssl.create_default_context()
context.check_hostname = False  # Disable hostname checking
context.verify_mode = ssl.CERT_NONE  # Disable certificate verification

# Connect to the server
with socket.create_connection((HOST, PORT)) as client_socket:
    with context.wrap_socket(client_socket, server_hostname=HOST) as secure_client:
        print("SSL connection established.")
        secure_client.sendall(b"Hello from SSL Client!")
        data = secure_client.recv(1024).decode('utf-8')
        print(f"Received: {data}")
