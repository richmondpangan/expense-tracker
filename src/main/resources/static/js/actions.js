const toggler = document.querySelector(".main-collapse-btn");
toggler.addEventListener("click",function(){
    document.querySelector("#sidebar").classList.toggle("collapsed");
});


const sidebarToggler = document.querySelector(".sidebar-collapse-btn");
sidebarToggler.addEventListener("click", function() {
    document.querySelector("#sidebar.collapsed").classList.remove("collapsed");
});