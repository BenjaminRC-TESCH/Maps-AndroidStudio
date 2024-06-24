const { Router } = require('express');
const router = Router();

const { signup, signin } = require('../controllers/auth.controller');

router.post('/auth/signup', signup);

router.post('/auth/login', signin);

module.exports = router;
