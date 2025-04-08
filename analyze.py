import pyshark

# Load the .pcapng file
pcap_file = 'capture.pcapng'  # Replace with your .pcapng file path
capture = pyshark.FileCapture(pcap_file)

print("Analyzing packets...")
for packet in capture:
    try:
        # Extract source and destination IP addresses
        src_ip = '127.0.0.1'
        dst_ip = '127.0.0.1'
        # Extract protocol and payload
        protocol = packet.transport_layer  # e.g., TCP/UDP
        payload = packet.get('data', None)  # Get raw data if available

        print(f"Source: {src_ip}, Destination: {dst_ip}, Protocol: {protocol}, Payload: {payload}")
    except AttributeError:
        # Skip packets without IP or transport layers
        continue
