import socket
import ssl

# Server setup
HOST = '127.0.0.1'
PORT = 8443

context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
context.load_cert_chain(certfile="cert.pem", keyfile="key.pem")

with socket.socket(socket.AF_INET, socket.SOCK_STREAM, 0) as server_socket:
    server_socket.bind((HOST, PORT))
    server_socket.listen(5)
    print(f"Server listening on {HOST}:{PORT}")

    with context.wrap_socket(server_socket, server_side=True) as secure_socket:
        while True:
            client_socket, addr = secure_socket.accept()
            print(f"Connection established with {addr}")
            data = client_socket.recv(1024).decode('utf-8')
            print(f"Received: {data}")
            client_socket.sendall(b"Hello from SSL Server!")
            client_socket.close()
