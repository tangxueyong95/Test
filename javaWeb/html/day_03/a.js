var n = 1;

function a() {
    document.getElementById("d1").src = "img/" + n + ".jpg";
    if (n == 4) {
        n = 0;
    } else {
        n++;
    }
}

window.setInterval("a()", 1000);