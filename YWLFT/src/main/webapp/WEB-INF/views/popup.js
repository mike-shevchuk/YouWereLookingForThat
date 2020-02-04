/*"permissions" : ["activeTab"]*/

function getCurrentTabUrl(callback) {
    var queryInfo = {
        active: true,
        currentWindow: true
    };
    /*
    chrome.tabs.query({'active': true, 'windowId': chrome.windows.WINDOW_ID_CURRENT},
        function(tabs){
            //alert(tabs[0].url);
            document.getElementById("resultUrl").innerHTML = tabs[0].url;
        }
    );
    */
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
        renderURL(url);
    });
});