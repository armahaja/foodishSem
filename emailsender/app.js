const express = require('express');
const bodyParser = require('body-parser');
const exphbs = require('express-handlebars');
const path = require('path');
const nodemailer = require('nodemailer');
const randomstring = require('randomstring');
const secretToken = randomstring.generate();

const app = express();

const sender = 'foodish.sem@gmail.com';

app.engine('handlebars', exphbs());

app.set('view engine', 'handlebars');

app.use('/public', express.static(path.join(__dirname, 'public')));

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/', (req, res) => {
  res.render('contact');
});

app.post('/send', (req, res) => {
  const output = `
    <ul> 
    <br>Hi ${req.body.name}, <br>
    <br> Thank you for registering!</br>
    <br> Please verify your email by using the following link: <br>
    <br> http://foodish.com/userEmail=${req.body.email}&UserToken=${secretToken}<br>
    <br> Enjoy! <br>
    </ul>
  `;

  let transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.EMAIL || sender, 
        pass: process.env.PASSWORD || 'Foodish1~' 
    },
        tls:{
      rejectUnauthorized:false
    }
});

const receiver = req.body.email;

let mailOptions = {
    from: sender, 
    to: receiver, 
    subject: 'Email verification test',
    text: 'Hi',
    html: output
};

  transporter.sendMail(mailOptions, (error, info) => {
      if (error) {
          return console.log(error);
      }
      console.log('Message sent: %s', info.messageId);   
      console.log('Preview URL: %s', nodemailer.getTestMessageUrl(info));

  });
  });

app.listen(3000, () => console.log('Server started...'));

