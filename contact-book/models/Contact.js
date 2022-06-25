var mongoose = require('mongoose');

//DB schema
var contactSchema = mongoose.Schema({
  name:{type:String, required:true, unique:true}, //name은 값이 반드시 입력되어야함(required), 값이 중복되면 안된다(unique)
  email:{type:String},
  phone:{type:String}
});

var Contact = mongoose.model('contact', contactSchema); 

module.exports = Contact;