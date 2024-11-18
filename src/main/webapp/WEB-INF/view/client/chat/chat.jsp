<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat with Seller</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        var socket = new WebSocket("ws://localhost:8080/chat");

        socket.onmessage = function (event) {
            var message = event.data;
            $('#chatBox').append('<p>' + message + '</p>');
        };

        function sendMessage() {
            var message = $('#messageInput').val();
            socket.send(message);
            $('#messageInput').val('');
        }
    </script>
</head>

<body>
    <div class="container mt-5">
        <h1>Chat with Seller</h1>
        <div id="chatBox" style="height: 300px; overflow-y: scroll; border: 1px solid #ccc; padding: 10px;">
            <!-- Chat messages will appear here -->
        </div>
        <div class="mt-3">
            <input id="messageInput" type="text" class="form-control" placeholder="Type a message" />
            <button class="btn btn-primary mt-2" onclick="sendMessage()">Send</button>
        </div>
    </div>
</body>

</html>