import os
BASE_DIR = os.path.dirname(os.path.dirname(__file__))

SECRET_KEY = '!s!9*o3@vy+-ql3vdk%=#56yzvu^g!t60*$f_9s8d*8*z+wj6&'

DEBUG = True

TEMPLATE_DEBUG = True

ALLOWED_HOSTS = []

NSTALLED_APPS = (
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
)

MIDDLEWARE_CLASSES = (
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
)

ROOT_URLCONF = 'smartcloset.urls'

WSGI_APPLICATION = 'smartcloset.wsgi.application'

LANGUAGE_CODE = 'es-AR'

TIME_ZONE = 'UTC-3'

USE_I18N = True

USE_L10N = True

USE_TZ = True

STATIC_URL = '/static/'