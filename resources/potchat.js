var previousMessages = [];

function onMessagesFetch() {
    var messages = this.responseText.split("\n");
    if (messages.count && previousMessages.count && messages[0] === previousMessages[0]) {
        console.log("messages haven't changed. nothing to do"); // NOCOMMIT
        previousMessages = messages;
        return;
    }
    previousMessages = messages;

    console.log("new messages:", messages); // NOCOMMIT

    var messagesDiv = document.getElementById('messages');

    messagesDiv.innerHTML = null;

    for (message of messages) {
        var messageEle = document.createElement('p');
        messageEle.innerText = message;
        messagesDiv.appendChild(messageEle);
    }
}

function updateMessages() {
    console.log("updating messages..."); // NOCOMMIT
    var request = new XMLHttpRequest();
    request.addEventListener("load", onMessagesFetch);
    request.open("GET", "/messages");
    request.send();
}

window.onload = function() {
    window.setInterval(updateMessages, 1000);
}
