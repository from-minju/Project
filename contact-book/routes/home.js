var express = require('express');
var router = express.Router(); //Router함수 초기화

// Home
router.get('/', function(req, res){
  res.redirect('/contacts');
});
//'/'에 get 요청이 오는 경우 : /contacts로 redirect하는 코드

module.exports = router;
