const express = require('express');
const bcrypt = require('bcrypt');
const passport = require('passport');
const User = require('../models/user');
const { Order } = require('../models');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');

const router = express.Router();

//회원가입 페이지
router.route('/register')
    .get(async (req, res, next) => {
        res.render('register', {
            title: require('../package.json').name,
            port: process.env.PORT
        });
    })
    .post(async (req, res, next) => {
        const { id, password, name, addr, ph } = req.body;

        if (!password) return next('비밀번호를 입력하세요.');

        const user = await User.findOne({ where: { id } });
        if (user) {
            next('이미 등록된 사용자 아이디입니다.');
            return;
        }

        try {
            const hash = await bcrypt.hash(password, 12);
            await User.create({
                id,
                password: hash,
                name,
                addr,
                ph
            });

            res.redirect('http://localhost:5000');

        } catch (err) {
            console.error(err);
            next(err);
        }
    });


//로그인
router.get('/login', isNotLoggedIn, (req, res, next) => {
    res.render('login',{
        title: require('../package.json').name,
        port: process.env.PORT
    });
});

router.post('/login', (req, res, next) => {
    passport.authenticate('local', (authError, user, info) => {
        if (user) req.login(user, loginError => res.redirect('/'));
        else next(`Login fail!`);
    })(req, res, next);
})


router.get('/logout', (req, res, next) => {
    req.logout();
    req.session.destroy();
    res.redirect('/');
});

router.get('/mypage', isLoggedIn, async(req, res, next) => {
    try {
        res.locals.user = req.user;

        const user = await User.findOne({
            where: { id: req.user.id }
        });

        const order = await Order.findAll({
            where: { ou_id: req.user.id }
        });


        res.render('mypage', {
            title: require('../package.json').name,
            port: process.env.PORT,
            user, order
        });


    } catch (err) {
        console.error(err);
        next(err);
    }
})

module.exports = router;
