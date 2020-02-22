var title;

function getCurrentTabUrl() {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        var tab = tabs[0];
        title = tab.title;
        document.getElementById("resultUrl").innerHTML = title;
    });


    chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
            chrome.tabs.query({active: true, currentWindow: true}, function(tabs){ title = tabs[0].title; });
            // read changeInfo data and do something with it (like read the url)
            if (changeInfo.url) {
                // do something here
                //title = getCurrentTabUrl();
                alert(title);
                post();
            }
        }
    );



}

function renderURL(statusText) {    document.getElementById('status').textContent = statusText; }

document.addEventListener('DOMContentLoaded', function() {
    getCurrentTabUrl(function(url) { //       renderURL(url);
    });
});




function post() {
//"*://*localhost:8080/*",
    // window.onload = function () {
    //$("#connectID").click(function () {
    var idVal = 1;
    var contentVal = title;
    var url = 'http://localhost:8080/YWLFT_war_exploded/articles/' + contentVal;
    var jsonString = JSON.stringify({id: idVal, content: contentVal});
    console.log(title);
    console.error(jsonString);
    $.ajax({
        /*	 headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },	*/
        type: 'POST',
        //method: 'POST',
        contentType: 'application/json; charset=utf-8',
        //contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        url: url,
        //data: JSON.stringify(jsonString), // Note it is important
        data: jsonString,
        //dataType: 'json',

        success: function (data, status, xhr) {
            console.log("work sucssesfull");
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });
    //});
    //   };
}