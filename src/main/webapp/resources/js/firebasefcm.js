var config = {
    apiKey: "AIzaSyAqGP2n9ZxNOKQw85TocEMUJYt1RzYZyhI",
    authDomain: "demofcm-4805e.firebaseapp.com",
    databaseURL: "https://demofcm-4805e.firebaseio.com",
    projectId: "demofcm-4805e",
    storageBucket: "demofcm-4805e.appspot.com",
    messagingSenderId: "208566904854"
};

function send_notfication(token,message) {
    var url = 'https://fcm.googleapis.com/fcm/send';
    var fields = {id:token,data:message};
    var header = {'Authorization:key = AIzaSyAqGP2n9ZxNOKQw85TocEMUJYt1RzYZyhI'
        ,'Content-Type:application/json'};
}
firebase.initializeApp(config);
const messaging = firebase.messaging()
    .then(function(token){
    })


