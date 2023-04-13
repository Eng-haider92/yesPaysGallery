const imgBtn = document.querySelector(".img__btn");

imgBtn.addEventListener("click", ()=> {
    document.querySelector(".content").classList.toggle("s--signup");
});


function readURL(input) {
    if(input.files[0].size > 2097152){
        alert("File is too big!");
        this.value = "";
     }else if(input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#imgPreview')
                .attr('src', e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

var uploadField = document.getElementById("file");

uploadField.onchange = function() {
    if(this.files[0].size > 2097152){
       alert("File is too big!");
       this.value = "";
    };
};