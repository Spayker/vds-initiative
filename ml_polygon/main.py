import tensorflow as tf

print("Tensorflow version: {}".format(tf.__version__))

first_string = tf.constant('Analytics')
second_string = tf.constant(' Spayker')

combined_string = (first_string + second_string)
print(type(combined_string))
print(combined_string)

with tf.Session() as sess:
    result = sess.run(combined_string)
    print(result)