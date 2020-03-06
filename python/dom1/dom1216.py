# def normalize(name):
#     return name.lower().capitalize()
# # 测试:
# L1 = ['adam', 'LISA', 'barT']
# r=map(normalize, L1)
# L2 = list(r)
# print(L2)

# from functools import reduce
# def prod(L):
#     def p(x,y):
#         return x*y
#     return reduce(p,L)
#
# print('3 * 5 * 7 * 9 =', prod([3, 5, 7, 9]))
# if prod([3, 5, 7, 9]) == 945:
#     print('测试成功!')
# else:
#     print('测试失败!')

# from functools import reduce
#
# def str2float(s):
#     i=s.index('.')
#     l=s[:i]+s[i+1:]
#     DIGITS = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}
#     def char2num(s):
#         return DIGITS[s]
#     def fn(x,y):
#        return x*10+y
#     return reduce(fn,map(char2num,l))/(10**i)
#
# print('str2float(\'123.456\') =', str2float('123.456'))
# if abs(str2float('123.456') - 123.456) < 0.00001:
#     print('测试成功!')
# else:
#     print('测试失败!')

# def _odd_iter():
#     n = 1
#     while True:
#         n = n + 2
#         yield n
#
# def _not_divisible(n):
#     return lambda x: x % n > 0
#
# def primes():
#     yield 2
#     it = _odd_iter() # 初始序列
#     while True:
#         n = next(it) # 返回序列的第一个数
#         yield n
#         it = filter(_not_divisible(n), it) # 构造新序列
#
# # 打印1000以内的素数:
# for n in primes():
#     if n < 100:
#         print(n)
#     else:
#         break

# def is_palindrome(n):
#     s=str(n)
#     return s==s[::-1]
#
# # 测试:
# output = filter(is_palindrome, range(1, 1000))
# print('1~1000:', list(output))
# if list(filter(is_palindrome, range(1, 200))) == [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55, 66, 77, 88, 99, 101, 111, 121, 131, 141, 151, 161, 171, 181, 191]:
#     print('测试成功!')
# else:
#     print('测试失败!')

# L = [('Bob', 75), ('Adam', 92), ('Bart', 66), ('Lisa', 88)]
# def by_name(t):
#     return t[0]
# L1 = sorted(L, key=by_name)
# print(L1)
# def by_score(t):
#     return t[1]
# L2 = sorted(L, key=by_score,reverse=True)
# print(L2)

def createCounter():
    i=0
    def counter():
        return i+1
    return counter

# 测试:
counterA = createCounter()
print(counterA(), counterA(), counterA(), counterA(), counterA()) # 1 2 3 4 5
counterB = createCounter()
if [counterB(), counterB(), counterB(), counterB()] == [1, 2, 3, 4]:
    print('测试通过!')
else:
    print('测试失败!')