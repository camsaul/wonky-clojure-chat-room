var previousMessages = [];

function updateDisplayedMessages(messages) {
    console.log("updating messages..."); // NOCOMMIT
    console.log("new messages:", messages); // NOCOMMIT

    var messagesDiv = document.getElementById('messages');

    messagesDiv.innerHTML = null;

    for (message of messages) {
        var messageEle = document.createElement('p');
        messageEle.innerText = message;
        messagesDiv.appendChild(messageEle);
    }
}

function onMessagesFetch() {
    var messages = this.responseText.split("\n");

    var messagesChanged = messages.length && (!previousMessages.length ||
                                              messages.length !== previousMessages.length ||
                                              (messages[messages.length - 1] !== previousMessages[previousMessages.length - 1]));
    previousMessages = messages;

    if (messagesChanged) updateDisplayedMessages(messages);
}

function updateMessages() {
    var request = new XMLHttpRequest();
    request.addEventListener("load", onMessagesFetch);
    request.open("GET", "/messages");
    request.send();
}

window.onload = function() {
    window.setInterval(updateMessages, 1000);
}
