import re
import psycopg2 as ps2
from collections import Counter
from uk_stemmer import UkStemmer
from nltk.tokenize import TweetTokenizer

conn = ps2.connect("dbname = FirstDB user=postgres password = 54321")

cur = conn.cursor()

# Перетворюємо слова пошукових запитів в множину стем
stemer = UkStemmer()
regex = re.compile('[^а-яА-Я ]')
stem_cache = {}

def get_stem(token):
    stem = stem_cache.get(token, None)
    if stem:
        return stem
    token = regex.sub('', token).lower()
    stem = stemer.stem_word(token)
    stem_cache[token] = stem
    return stem

stem_count = Counter()
tokenizer = TweetTokenizer()

def count_unique_tokens_in_question(ques):
    for tweet_series in ques:
        tweet = tweet_series
        tokens = tokenizer.tokenize(tweet)
        for token in tokens:
            stem = get_stem(token)
            stem_count[stem] += 1


#vocab = sorted(stem_count, key=stem_count.get, reverse=True)[:10]

#token_2_idx = {vocab[i] : i for i in range(10)}

#print(token_2_idx)

'''
cur.execute("select distinct uid from us")
q = cur.fetchall()
conn.commit()

st=[]
st = [q[x][0] for x in range(len(q))]

uid =[]
for i in st:
    cur.execute("select ques from us where uid = %s", str(i))
    q = cur.fetchall()
    conn.commit()
    if len(q)>=10:
        uid.append(i)

question = dict()

# Дістаємо пошукові запити і прив'язуємо їх до id користувачів
for i in uid:
    cur.execute("select ques from us where uid = %s", str(i))
    q = cur.fetchall()
    conn.commit()
    for j in q:
        question[j[0]] = i

'''

cur.close()
conn.close()


    '''
    print("Recommending " + str(num) + " products similar to " + item(item_id) + "...")
    print("-------")
    '''