# TensorFlow and tf.keras
import tensorflow as tf
from tensorflow import keras
import tempfile

# Helper libraries
import numpy as np
import matplotlib.pyplot as plt
import os
import random
import json
import requests

fashion_mnist = keras.datasets.fashion_mnist
(train_images, train_labels), (test_images, test_labels) = fashion_mnist.load_data()

# scale the values to 0.0 to 1.0
train_images = train_images / 255.0
test_images = test_images / 255.0

# reshape for feeding into the model
train_images = train_images.reshape(train_images.shape[0], 28, 28, 1)
test_images = test_images.reshape(test_images.shape[0], 28, 28, 1)

class_names = ['T-shirt/top', 'Trouser', 'Pullover', 'Dress', 'Coat',
               'Sandal', 'Shirt', 'Sneaker', 'Bag', 'Ankle boot']

print('\ntrain_images.shape: {}, of {}'.format(train_images.shape, train_images.dtype))
print('test_images.shape: {}, of {}'.format(test_images.shape, test_images.dtype))

model = keras.Sequential([
    keras.layers.Conv2D(input_shape=(28, 28, 1), filters=8, kernel_size=3,
                        strides=2, activation='relu', name='Conv1'),
    keras.layers.Flatten(),
    keras.layers.Dense(10, activation=tf.nn.softmax, name='Softmax')
])
model.summary()

testing = False
epochs = 5

model.compile(optimizer=tf.train.AdamOptimizer(),
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])
model.fit(train_images, train_labels, epochs=epochs)

test_loss, test_acc = model.evaluate(test_images, test_labels)
print('\nTest accuracy: {}'.format(test_acc))

MODEL_DIR = tempfile.gettempdir()
os.environ["MODEL_DIR"] = MODEL_DIR

version = 1
export_path = os.path.join(MODEL_DIR, str(version))
print('export_path = {}\n'.format(export_path))
if os.path.isdir(export_path):
    print('\nAlready saved a model, cleaning up\n')
    os.popen('rm -rf /tmp/1')

tf.saved_model.simple_save(
    keras.backend.get_session(),
    export_path,
    inputs={'input_image': model.input},
    outputs={t.name: t for t in model.outputs})

print('\nSaved model:')
os.popen('ls -l /tmp/1')

os.popen(
    'nohup tensorflow_model_server --rest_api_port=8501 --model_name=fashion_model --model_base_path="${MODEL_DIR}" >server.log 2>&1')


def show(idx, title):
    plt.figure()
    plt.imshow(test_images[idx].reshape(28, 28))
    plt.axis('off')
    plt.title('\n\n{}'.format(title), fontdict={'size': 16})
    plt.show()


rando = random.randint(0, len(test_images) - 1)
# show(rando, 'An Example Image: {}'.format(class_names[test_labels[rando]]))

data = json.dumps({"signature_name": "serving_default", "instances": test_images[0:3].tolist()})
print('Data: {} ... {}'.format(data[:50], data[len(data)-52:]))

headers = {"content-type": "application/json"}
json_response = requests.post('http://localhost:8501/v1/models/fashion_model:predict', data=data, headers=headers)
predictions = json.loads(json_response.text)['predictions']

show(0, 'The model thought this was a {} (class {}), and it was actually a {} (class {})'.format(
  class_names[np.argmax(predictions[0])], test_labels[0], class_names[np.argmax(predictions[0])], test_labels[0]))
