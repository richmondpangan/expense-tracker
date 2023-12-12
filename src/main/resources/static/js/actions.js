const toggler = document.querySelector(".main-collapse-btn");
toggler.addEventListener("click",function(){
    document.querySelector("#sidebar").classList.toggle("collapsed");
});


const sidebarToggler = document.querySelector(".sidebar-collapse-btn");
sidebarToggler.addEventListener("click", function() {
    document.querySelector("#sidebar.collapsed").classList.remove("collapsed");
});


function saveAccount() {
    // Collect data from the form
    let formData = {
        accountType: $('#accountTypeInput').val(),
        accountName: $('#accountNameInput').val(),
        balance: $('#accountBalanceInput').val()
    };
    
    // User ID
    let userId = 1;

    // Make an AJAX request to save the account
    $.ajax({
        type: 'POST',
        url: '/users/' + userId + '/accounts', // controller endpoint
        data: JSON.stringify(formData), // If formData is a JSON object
        contentType: 'application/json',
        success: function (response) {
            // Handle success (e.g., close the modal, update UI)
            $('#addNewAccount').modal('hide');
            
            // Reload the page to reflect the updated account list
            location.reload();
            
            console.log('Success', response);
        },
        error: function (error) {
            // Handle error (e.g., show an error message)
            console.error('Error saving account:', error);
        }
    });
}