
function getCurrentTabUrl(callback) {
    var queryInfo = {
        active: true,
        currentWindow: true
    };

        chrome.tabs.query(queryInfo, function(tabs) {
        var tab = tabs[0];
        var title = tab.title;
        var url = tab.url;
        document.getElementById("resultUrl").innerHTML = title;
        callback(url);
    });
}

function renderURL(statusText) {
    document.getElementById('status').textContent = statusText;
}

document.addEventListener('DOMContentLoaded', function() {
    getCurrentTabUrl(function(url) {
 //       renderURL(url);
    });
});


//"*://*localhost:8080/*",
window.onload = function() {
    $("#connectID").click(function () {
        var idVal = 1;
        var contentVal = "Message java catch";     
        var url = 'http://localhost:8080/YWLFT_war_exploded/articles/' + idVal;
        var jsonString = JSON.stringify({id: idVal, content: contentVal});
       // console.error(jsonString);
        $.ajax({
            type: 'POST',
            //method: 'POST',
            url: url,
            contentType: 'application/json',
            data: jsonString,
            
            success: function (data, status, xhr) {
                console.log("work successesull");
                //$("#result").html(data + " link: <a href='" + url + "'>" + url + "</a>");
            },
            error: function (xhr, status, error) {
              //  console.error("data --> " + data + " status --> " + status + " xhr --> " + xhr);
                console.error(error);
            }

        });
    });
};