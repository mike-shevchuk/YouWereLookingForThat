import psycopg2 as ps2
from uk_stemmer import UkStemmer
import re

conn = ps2.connect("dbname = FirstDB user=postgres password = 54321")

cur = conn.cursor()

cur.execute("select * from us where uid = 3")
q = cur.fetchall()
conn.commit()

quest = []
for i in q:
    quest.append(i[1])

stem = UkStemmer()
set_stem = set()
for i in quest:
    st = i.split(" ")
    for j in st:
        test_stem = stem.stem_word(j)
        set_stem.add(test_stem)

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




cur.close()
conn.close()