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

function updateAccountsTableData(data) {
    // Clear the existing table body
    let tableBody = $('#accountsTableBody');
    tableBody.empty();

    for (let i = 0; i < data.length; i++) {
        let account = data[i];
        // Format the balance to always display two decimal places
        let formattedBalance = Number(account.balance).toFixed(2);
        tableBody.append('<tr>'
                + '<td>' + account.accountType + '</td>'
                + '<td>' + account.accountName + '</td>'
                + '<td>' + formattedBalance + '</td>'
                + '<td>' + '<button class="btn btn-success" type="button">Edit</button>'
                + '<button class="btn btn-danger" type="button" onclick="openDeleteAccountModal(' + account.user.userId + ', ' + account.accountId + ')">Delete</button>'
                + '</td>'
                + '</tr>');
    }
}

function addedSuccessfully() {        
    $.ajax({
       type: 'GET',
       url: '/users/' + userId + '/accounts/fetchUpdate',
       dataType: 'json',
       success: function (data) {
            updateAccountsTableData(data);
            console.log('addedSuccessfully Modal working');
       },
       error: function (error) {
           console.log('Error: ', error);
       }
    });
    // Hide sucess modal after clicking the 'okay' button
    $('#successModal').modal('hide');
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


let deleteUserAccountInfo = {}; // Global variable to store user and account information

function openDeleteAccountModal(userIdd, accountId) {
    // Update the browser's URL
//    let state = { userIdd: userIdd, accountId: accountId };
//    history.pushState(state, null, '/users/' + userIdd + '/accounts/' + accountId);

    deleteUserAccountInfo = { userIdd: userIdd, accountId: accountId };
    
    $.ajax({
       type: 'GET',
       url: '/users/' + userIdd + '/accounts/' + accountId,
       success: function () {
           $('#deleteAccountModal').modal('show');
           console.log('userId:', userIdd);
           console.log('accountId:', accountId);
           console.log('openDeleteAccountModal working');
       },
       error: function (error) {
           console.log('Error:', error);
       }
    });
}

function confirmDelete() {
    let userIdd = deleteUserAccountInfo.userIdd;
    let accountId = deleteUserAccountInfo.accountId;
    
    let url = '/users/' + userIdd + '/accounts/' + accountId + '/delete';
    console.log('DELETE URL:', url);
    
    $.ajax({
       type: 'DELETE',
       url: '/users/' + userIdd + '/accounts/' + accountId + '/delete',
       success: function (response) {
           console.log(response);
           $.ajax({
                type: 'GET',
                url: '/users/' + userId + '/accounts/fetchUpdate',
                dataType: 'json',
                success: function (data) {
                    updateAccountsTableData(data);
                    $('#deleteAccountModal').modal('hide');
                },
                error: function (error) {
                    console.log('Error', error);
                }
           });
       },
       error: function (error) {
           console.log('Error', error);
       }
    });
}

function cancelDelete() {
    $('#deleteAccountModal').modal('hide');
}