const toggler = document.querySelector(".main-collapse-btn");
toggler.addEventListener("click",function(){
    document.querySelector("#sidebar").classList.toggle("collapsed");
});


const sidebarToggler = document.querySelector(".sidebar-collapse-btn");
sidebarToggler.addEventListener("click", function() {
    document.querySelector("#sidebar.collapsed").classList.remove("collapsed");
});

    
// User ID
let userId = 1;

function saveAccount() {
    // Collect data from the form
    let formData = {
        accountType: $('#accountTypeInput').val(),
        accountName: $('#accountNameInput').val(),
        balance: $('#accountBalanceInput').val()
    };

    // Make an AJAX request to save the account
    $.ajax({
        type: 'POST',
        url: '/users/' + userId + '/accounts/add', // controller endpoint
        data: JSON.stringify(formData), // If formData is a JSON object
        contentType: 'application/json',
        success: function (response) {
            // Hide Add New Account modal after successfully adding a new account
            $('#addAccountModal').modal('hide');
            console.log(response);
            
            //updateAccountsTable();
            
            $('#successModal').modal('show');
        },
        error: function (error) {
            // Handle error (e.g., show an error message)
            console.error('Error adding a new account:', error);
        }
    });
}

function addedSuccessfully() {        
    $.ajax({
       type: 'GET',
       url: '/users/' + userId + '/accounts/fetchUpdate',
       dataType: 'json',
       success: function (data) {
            // Clear the existing table body
            let tableBody = $('#accountsTableBody');
            tableBody.empty();
            
            for (let i = 0; i < data.length; i++) {
                let account = data[i];
                tableBody.append('<tr>'
                        + '<td>' + account.accountType + '</td>'
                        + '<td>' + account.accountName + '</td>'
                        + '<td>' + account.balance + '</td>'
                        + '<td>' + '<button class="btn btn-success" type="button">Edit</button>' + '</td>'
                        + '<td>' + '<button class="btn btn-danger" type="button">Delete</button>' + '</td>'
                       + '</tr>');
            }
            console.log('addedSuccessfully Modal working');
       },
       error: function (error) {
           console.log('Error: ', error);
       }
    });
    // Hide sucess modal after clicking the 'okay' button
    $('#successModal').modal('hide');
    
    // Reload the page to reflect the updated account list
    //location.reload();
}

function openAddAccountModal() {
    $.ajax({
       type: 'GET',
       url: '/users/' + userId + '/accounts/add',
       success: function () {
           $('#addAccountModal').modal('show');
           console.log('openAddAccountModal working');
       },
       error: function (error) {
           console.log('Error:', error);
       }
    });
}

//function updateAccountsTable() {
//    let tableBody = $('#accountsTableBody');
//    
//    // Clear the existing table body
//    tableBody.empty();
//    
//    // Iterate over the accounts and append rows to the table
//    $.each(accounts, function(_, account) {
//        let row = $('<tr>');
//        row.append('<td>' + account.accountType + '</td>');
//        row.append('<td>' + account.accountName + '</td>');
//        row.append('<td>' + account.balance + '</td>');
//        row.append('<td>' +
//            '<button class="btn btn-success" type="button">Edit</button>' +
//            '<button class="btn btn-danger" type="button">Delete</button>' +
//            '</td>');
//
//        tableBody.append(row);
//    });
//}