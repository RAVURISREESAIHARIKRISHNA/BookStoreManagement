function checkPassword(){
  let passw1 = document.getElementById("pass").value;
  let passw2 = document.getElementById("retype").value;
  if(passw1===passw2){
    return true;
  }
  else{
    alert('passwords not matching');
    document.getElementById("pass").value="";
    document.getElementById("retype").value="";
    return false;
  }
}