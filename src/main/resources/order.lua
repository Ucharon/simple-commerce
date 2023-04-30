--商品id
local goodsId = ARGV[1]:gsub('"','')
--用户id
local userId = ARGV[2]:gsub('"','')
--商品价格
local goodsPrice = tonumber(ARGV[3])

--库存key
local stockKey = 'goods:stock:' .. goodsId
--用户余额key
local balanceKey = 'user:balance:' .. userId

--1. 判断库存是否充足
local stock = tostring(redis.call('get', stockKey))
local stockNum = tonumber(string.sub(stock, 1, -2))
if (stockNum <= 0) then
    --1.1库存不足
    return 1
end
--1.2库存充足

--2. 判断用户余额是否足够
local balancePrice = tonumber(redis.call('get', balanceKey))
if (balancePrice < goodsPrice) then
    --2.1 余额不够
    return 2
end
--2.2余额充足

--3. 扣减库存
stockNum = stockNum - 1
redis.call('set', stockKey, tostring(stockNum) .. 'L')


--4. 扣减余额
-- 将金额乘以100并向下取整，得到整数
local balanceInt = math.floor(balancePrice * 100)
local goodsInt = math.floor(goodsPrice * 100)
-- 进行整数相减
local result_int = balanceInt - goodsInt
-- 将结果除以100转换为小数
local result = result_int / 100
redis.call('set', balanceKey, string.format("%.2f", result))
return 0