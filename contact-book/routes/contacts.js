var express = require('express');
var router = express.Router();
var Contact = require('../models/Contact');

// Index (생성된 주소록의 이름 목록을 보여준다)
router.get('/', function(req, res){ //'/contacts'에 get 요청이 오는 경우
  Contact.find({}, function(err, contacts){ //모델.find(검색조건, callback함수), DB에서 검색조건에 맞는 모델을 찾고 콜백함수를 호출하는 함수
    if(err) return res.json(err);//에러가 있다면 에러를 json형태로 웹브라우저에 표시
    res.render('contacts/index', {contacts:contacts});//에러가 없다면 검색 결과를 받아 views/contacts.index.ejs를 render(페이지를 다이나믹하게 제작)
  });
});

// New (이름, 이메일 주소, 전화번호를 받는 form을 만들어서 이 정보를 서버로 전달할 수 있게)
router.get('/new', function(req, res){ // '/contacts/new'에 get요청이 오는 경우
  res.render('contacts/new'); //새로운 주소록을 만드는 form이 있는 views/contacts/new.ejs를 render
});

//search
router.get('/search', function(req, res){
  res.render('contacts/search');
})

// router.post('/search', function(req, res){
//   Contact.find({name: keyword}, function(err, contacts){
//     res.render('contacts/searchindex', {contacts:contacts})
//   })
// });

// create (서버가 이 정보를 사용해서 DB에 정보를 생성)
router.post('/', function(req, res){ //'contacts'에 post요청이 오는 경우
  Contact.create(req.body, function(err, contact){//DB에 데이터를 생성하는 함수
    if(err) return res.json(err);
    res.redirect('/contacts');
  });
});

// show (index에서 하나를 선택하면 해당 데이터를 보여줌)
router.get('/:id', function(req, res){
  Contact.findOne({_id:req.params.id}, function(err, contact){//DB에서 해당 model의 document를 하나 찾는 함수
    if(err) return res.json(err);
    res.render('contacts/show', {contact:contact});
  });
});

// edit (해당 데이터를 수정할 수 있는 form을 만들어서 이 정보를 서버로 전달할 수 있게)
router.get('/:id/edit', function(req, res){
  Contact.findOne({_id:req.params.id}, function(err, contact){ 
    if(err) return res.json(err);
    res.render('contacts/edit', {contact:contact});
  });
});

// update (서버가 이 정보를 사용해서 DB에 정보를 수정)
router.put('/:id', function(req, res){
  Contact.findOneAndUpdate({_id:req.params.id}, req.body, function(err, contact){//DB에서 해당model의 document를 찾아 그 data를 수정
    if(err) return res.json(err);
    res.redirect('/contacts/'+req.params.id);
  });
});

// destroy (해당 데이터를 삭제할 수 있다.)
router.delete('/:id', function(req, res){
  Contact.deleteOne({_id:req.params.id}, function(err){ //삭제하는 함수
    if(err) return res.json(err);
    res.redirect('/contacts');//data삭제 후 '/contacts'로 redirect
  });
});






module.exports = router;