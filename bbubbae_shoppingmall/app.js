const path = require('path');

const express = require('express');
const morgan = require('morgan');
const dotenv = require('dotenv');
const cookieParser = require('cookie-parser');
const session = require('express-session');

const nunjucks = require('nunjucks');
const { sequelize } = require('./models');

const passport = require('passport');
const passportConfig = require('./passport');

const userRouter = require('./routes/user');
const productRouter = require('./routes/product');
const orderRouter = require('./routes/order');

dotenv.config();
passportConfig();

const app = express();
app.set('port', process.env.PORT || 3000);

app.set('view engine', 'html');
nunjucks.configure(path.join(__dirname, 'views'), {
    express: app,
    watch: true,
});

sequelize.sync({ force: false })
  .then(() => {})
  .catch(err => console.error(err));

app.use(
    morgan('dev'),
    express.static(path.join(__dirname, 'public')),
    express.static(path.join(__dirname, 'uploads')),
    express.json(),
    express.urlencoded({ extended: false }),
    cookieParser(process.env.SECRET),
    session({
        resave: false,
        saveUninitialized: false,
        secret: process.env.SECRET,
        cookie: {
            httpOnly: true,
            secure: false
        },
        name: 'session-cookie'
    })
);

app.use(passport.initialize());
app.use(passport.session());

app.use('/user', userRouter);
app.use('/order', orderRouter);
app.use('/product', productRouter);


app.use((req, res, next) => {
    res.locals.title = require('./package.json').name;
    res.locals.port = app.get('port');
    res.locals.user = req.user;
    res.render('index');
});

app.use((err, req, res, next) => {
    console.error(err);
    res.status(500).send(err);
});

app.listen(app.get('port'), () => {
    console.log(app.get('port'), '번 포트에서 대기 중');
});

module.exports = app;
